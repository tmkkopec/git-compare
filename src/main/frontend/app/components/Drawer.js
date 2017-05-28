'use strict';

import React from "react";
const uniqueId = require('lodash/uniqueId');

class Attribute extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <span className="mdl-list__item-primary-content">{this.props.attributeName}</span>
        )
    }
}

class Checkbox extends React.Component {
    constructor(props) {
        super(props);
        this.toggleChange = this.toggleChange.bind(this);
    }

    toggleChange(event) {
        // TODO handle event (i.e show/hide elements of ui at main page)
        console.log(event.target.checked)
    }

    render() {
        const id = this.props.id;
        return (
            <span className="mdl-list__item-secondary-action">
                <label className="mdl-switch mdl-js-switch mdl-js-ripple-effect"
                       htmlFor={"list-switch-" + id}>
                    <input type="checkbox" id={"list-switch-" + id} className="mdl-switch__input"
                           onChange={(event) => this.toggleChange(event)} defaultChecked={this.props.defaultChecked}/>
                </label>
            </span>
        )
    }
}

class Drawer extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="mdl-layout__drawer">
                <span className="mdl-layout-title">Filtering options</span>
                <nav className="mdl-navigation">
                    <ul className="mdl-list">
                        {this.props.attrs.map((attr, index) =>
                            <li className="mdl-list__item" key={uniqueId()}>
                                <Attribute attributeName={attr.value}/>
                                <Checkbox defaultChecked={true} id={index}/>
                            </li>
                        )}
                    </ul>
                </nav>
            </div>
        )
    }
}

export default Drawer;