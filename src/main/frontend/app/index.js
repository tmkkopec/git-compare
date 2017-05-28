'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const LoginPage = require('./components/LoginPage');
import LoginController from "./components/LoginController";

ReactDOM.render(
    <LoginController/>,
    document.getElementById('app')
);
