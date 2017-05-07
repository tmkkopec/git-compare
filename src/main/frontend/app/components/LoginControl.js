'use strict';

import React from 'react';
import $ from 'jquery';
import LoginPage from './LoginPage';
import HomePage from './HomePage';

class LoginControl extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: 'N/A',
            authenticated: false
        }
    }

    componentWillMount() {
        const self = this;
        $.ajax({
            async: false,
            type: "GET",
            url: '/user',
            success: function (data) {
                console.log(data);
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
            return <HomePage/>;
        else
            return <LoginPage/>;
    }
}

export default LoginControl;