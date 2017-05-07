'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const LoginPage = require('./components/LoginPage');
import LoginControl from "./components/LoginControl";

ReactDOM.render(
    <LoginControl/>,
    document.getElementById('app')
);
