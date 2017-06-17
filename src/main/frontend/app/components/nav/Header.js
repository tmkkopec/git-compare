'use strict';

import React from "react";
import PropTypes from "prop-types";

class Header extends React.Component {
    constructor(props) {
        super(props);
    }

    shouldComponentUpdate() {
        return false;
    }

    render() {
        return (
            <header className="mdl-layout__header">
                <div className="mdl-layout__header-row">
                    {/*Title*/}
                    <span className="mdl-layout-title">Git Compare</span>
                    {/*Spacer*/}
                    <div className="mdl-layout-spacer"/>
                    {/*Navigation*/}
                    <span className="mdl-typography--body-2">Logged in as {this.props.username}</span>
                    <nav className="mdl-navigation">
                        <a className="mdl-navigation__link mdl-typography--button" href=""
                           onClick={this.props.onLogout}>Logout</a>
                    </nav>
                </div>
            </header>
        )
    }
}

Header.propTypes = {
    username: PropTypes.string.isRequired,
    onLogout: PropTypes.func.isRequired
};

export default Header;