'use strict';

import React from "react";
import Modal from "boron/FadeModal";

class AddDialog extends React.Component {
    constructor(props) {
        super(props);
        this.showModal = this.showModal.bind(this);
        this.hideModal = this.hideModal.bind(this);
    }

    showModal() {
        this.refs.modal.show();
    }

    hideModal() {
        this.refs.modal.hide();
    }

    callback(event) {
        console.log(event);
    }

    render() {
        return (
            <div>
                <button
                    className="addButton mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--fab mdl-button--colored"
                    onClick={this.showModal}>
                    <i className="material-icons">add</i>
                </button>
                <Modal ref="modal" className="mdl-dialog">
                    <h4 className="mdl-dialog__title">Add GitHub user</h4>
                    <div className="mdl-dialog__content">
                        <p>
                            Type GitHub username
                        </p>
                    </div>
                    <div className="mdl-dialog__actions">
                        <button className="mdl-button" onClick={this.callback}>Add</button>
                        <button className="mdl-button mdl-js-button mdl-button--raised" onClick={this.hideModal}>
                            Close
                        </button>
                    </div>
                </Modal>
            </div>
        )
    }
}

export default AddDialog;