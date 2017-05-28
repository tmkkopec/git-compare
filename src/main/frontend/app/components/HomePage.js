'use strict';

import React from "react";
import Header from "./Header";
import Drawer from "./Drawer";
import AddDialog from "./AddDialog";
import PanelController from "./PanelController";
import $ from "jquery";

class Property {
    constructor(key, value) {
        this.key = key;
        this.value = value;
    }
}

const properties = [
    new Property('email', 'email'),
    new Property('followers', 'followers'),
    new Property('following', 'following'),
    new Property('public_gists', 'public gists'),
    new Property('hireable', 'is hireable'),
    new Property('location', 'location'),
    new Property('created_at', 'created at')
];

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            error: false
        };
        this.addPanel = this.addPanel.bind(this);
    }

    addPanel(username) {
        const url = "http://localhost:8080/users/" + username;
        const self = this;
        $.ajax({
            url: url,
            success: function (data) {
                self.setState({
                    error: false,
                    users: self.state.users.concat([data])
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                self.setState({
                    error: errorThrown
                })
            }
        })
    }

    render() {
        return (
            <div className="mdl-layout mdl-js-layout mdl-layout--fixed-header mdl-layout--fixed-drawer">
                <Header username={this.props.user.name} onLogout={this.props.onLogout}/>
                <Drawer attrs={properties}/>
                <main className="mdl-layout__content">
                    <div className="page">
                        <PanelController users={this.state.users}
                                         properties={properties}/>
                        <AddDialog token={this.props.token}
                                   addPanel={this.addPanel}/>
                    </div>
                </main>
            </div>
        )
    }
}

export default HomePage;