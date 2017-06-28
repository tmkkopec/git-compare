'use strict';

import React from "react";
import PropTypes from "prop-types";
import Property from "../util/Property";
const uniqueId = require('lodash/uniqueId');

class Checkbox extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const id = this.props.id;
        return (
            <li className="mdl-list__item">
                <span className="mdl-list__item-primary-content">{this.props.attributeName}</span>
                <span className="mdl-list__item-secondary-action">
                    <label className="mdl-switch mdl-js-switch mdl-js-ripple-effect"
                           htmlFor={"list-switch-" + id}>
                    <input type="checkbox" id={"list-switch-" + id} className="mdl-switch__input"
                           onChange={(event) =>
                               this.props.changeProperty(this.props.attributeName, event.target.checked)}
                           defaultChecked={this.props.defaultChecked}/>
                    </label>
                </span>
            </li>

        )
    }
}

Checkbox.propTypes = {
    attributeName: PropTypes.string.isRequired,
    changeProperty: PropTypes.func.isRequired,
    defaultChecked: PropTypes.bool.isRequired,
    id: PropTypes.number.isRequired
};

class Drawer extends React.Component {
    constructor(props) {
        super(props);
    }

    shouldComponentUpdate() {
        return false;
    }

    render() {
        return (
            <div className="mdl-layout__drawer">
                <span className="mdl-layout-title">Filtering options</span>
                <nav className="mdl-navigation">
                    <ul className="mdl-list">
                        {this.props.attrs.map((attr, index) =>
                            <Checkbox key={uniqueId()}
                                      attributeName={attr.name}
                                      defaultChecked={true}
                                      changeProperty={this.props.changeProperty}
                                      id={index}/>
                        )}
                    </ul>
                </nav>
            </div>
        )
    }
}

Drawer.propTypes = {
    attrs: PropTypes.arrayOf(PropTypes.instanceOf(Property)).isRequired,
    changeProperty: PropTypes.func.isRequired
};

export default Drawer;