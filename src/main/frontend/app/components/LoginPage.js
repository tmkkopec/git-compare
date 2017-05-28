'use strict';

const React = require('react');
const MdlGrid = require('./MdlGrid');
const MdlCell = require('./MdlCell');

class Title extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <MdlCell cellWidth="12">
                <h3 className="mdl-typography--display-4 mdl-color-text--grey-600">Git Compare</h3>
            </MdlCell>
        );
    }
}

class Link extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <MdlCell cellWidth="12">
                <p className="mdl-color-text--black">
                    Login with
                    <button className="loginButton mdl-button mdl-js-button mdl-button--raised"
                            onClick={this.props.onClickEvent}>GitHub
                    </button>
                </p>
            </MdlCell>
        )
    }
}

class LoginPage extends React.Component {
    constructor(props) {
        super(props);
    }

    _buildHref() {
        location.href = '/login';
    }

    render() {
        return (
            <div>
                <MdlGrid className="login-grid">
                    <Title/>
                </MdlGrid>
                <MdlGrid className="login-grid">
                    <Link onClickEvent={this._buildHref}/>
                </MdlGrid>
            </div>
        );
    }
}

module.exports = LoginPage;