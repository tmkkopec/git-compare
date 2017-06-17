'use strict';

import React from "react";
import $ from "jquery";
import * as Cookies from "js-cookie";
import LoginPage from "./LoginPage";
import HomePage from "./HomePage";

class LoginController extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: 'N/A',
            token: null,
            authenticated: false
        };
        this.handleLogout = this.handleLogout.bind(this);
    }

    handleLogout() {
        const self = this;
        $.ajax({
            async: false,
            headers: {'X-XSRF-TOKEN': Cookies.get('XSRF-TOKEN')},
            type: "POST",
            url: '/logout',
            success: function (data) {
                self.setState({
                    authenticated: false
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                self.setState({
                    username: 'N/A',
                    authenticated: true
                });
            }
        });
    }

    componentWillMount() {
        const self = this;
        $.ajax({
            async: false,
            type: "GET",
            url: '/user',
            success: function (data) {
                const username = data.userAuthentication.details.name;
                const token = data.details.tokenValue;
                self.setState({
                    username: username,
                    token: token,
                    authenticated: true
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                self.setState({
                    username: 'N/A',
                    authenticated: false
                });
            }
        });
    }

    render() {
        if (this.state.authenticated) {
            document.querySelector('title').innerHTML = 'Git Compare - Home page';
            return <HomePage
                user={{name: this.state.username}}
                onLogout={this.handleLogout}
                token={this.state.token}/>;
        }
        else {
            document.querySelector('title').innerHTML = 'Git Compare - Login';
            return <LoginPage/>;
        }
    }
}

export default LoginController;