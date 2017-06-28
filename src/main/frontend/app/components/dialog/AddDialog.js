'use strict';

import React from "react";
import PropTypes from "prop-types";
import Modal from "boron/FadeModal";
import $ from "jquery";
import DynamicList from "./DynamicList";

class Input extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.props.inputMounted();
    }

    render() {
        return (
            <input id="textInput" className="mdl-textfield__input" type="text"
                   onChange={this.props.handleChange}
                   value={this.props.value}
            />
        )
    }
}

class AddDialog extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            inputValue: '',
            isVisible: false,
            users: []
        };
        this.typingTimer = 0;
        this.doneTypingInterval = 2000;
        this.showModal = this.showModal.bind(this);
        this.hideModal = this.hideModal.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleHide = this.handleHide.bind(this);
        this.handleUserSelect = this.handleUserSelect.bind(this);
        this.addUser = this.addUser.bind(this);
        this.doneTyping = this.doneTyping.bind(this);
        this.inputMounted = this.inputMounted.bind(this);
    }

    showModal() {
        this.refs.modal.show();
    }

    hideModal() {
        this.refs.modal.hide();
    }

    handleHide() {
        this.setState({
            inputValue: '',
            isListVisible: false,
            users: []
        });
    }

    handleChange(event) {
        const value = event.target.value;

        this.setState({
            inputValue: value
        });
    }

    handleUserSelect(text) {
        this.setState({
            isListVisible: false,
            inputValue: text,
            users: []
        });
    }

    addUser(username) {
        this.hideModal();
        this.props.addPanel(username);
    }

    doneTyping() {
        const self = this;
        const value = this.state.inputValue;
        if (value !== '') {
            const url = 'https://api.github.com/search/users?q=' + value + 'in:login&' +
                'access_token=' + this.props.token;
            $.ajax({
                type: 'GET',
                url: url,
                success: function (data) {
                    self.setState({
                        isListVisible: true,
                        users: data.items
                    })
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(errorThrown)
                }
            })
        } else {
            self.setState({
                isListVisible: false,
                users: []
            })
        }
    }

    inputMounted() {
        const self = this;
        $('#textInput').keyup(function () {
            clearTimeout(self.typingTimer);
            if ($('#textInput').val()) {
                self.typingTimer = setTimeout(self.doneTyping, self.doneTypingInterval);
            }
        });
    }

    render() {
        return (
            <div className="addDialog">
                <button
                    className="addButton mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--fab mdl-color--grey-400"
                    onClick={this.showModal}>
                    <i className="material-icons">add</i>
                </button>
                <Modal ref="modal"
                       onHide={this.handleHide}>
                    <h4 className="mdl-dialog__title">Add GitHub user</h4>
                    <div className="mdl-dialog__content">
                        <p>
                            Type GitHub username
                        </p>
                        <div id="dynamicInput" className="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                            <Input
                                value={this.state.inputValue}
                                handleChange={this.handleChange}
                                inputMounted={this.inputMounted}/>
                            <DynamicList
                                list={this.state.users}
                                isVisible={this.state.isListVisible}
                                handleUserSelect={this.handleUserSelect}/>
                        </div>
                    </div>
                    <div className="mdl-dialog__actions">
                        <button className="mdl-button mdl-js-button mdl-button--raised"
                                onClick={() => this.addUser(this.state.inputValue)}>Add
                        </button>
                        <button className="mdl-button mdl-js-button mdl-button--raised"
                                onClick={this.hideModal}>
                            Close
                        </button>
                    </div>
                </Modal>
            </div>
        )
    }
}

AddDialog.propTypes = {
    token: PropTypes.string.isRequired,
    addPanel: PropTypes.func.isRequired
};

export default AddDialog;