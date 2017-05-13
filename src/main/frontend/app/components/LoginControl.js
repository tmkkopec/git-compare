'use strict';

import React from "react";
import $ from "jquery";
import * as Cookies from "js-cookie";
import LoginPage from "./LoginPage";
import HomePage from "./HomePage";

class LoginControl extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: 'N/A',
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
                self.setState({
                    username: username,
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
        if (this.state.authenticated)
            return <HomePage user={{name: this.state.username}} onLogout={this.handleLogout}/>;
        else
            return <LoginPage/>;
    }
}

export default LoginControl;