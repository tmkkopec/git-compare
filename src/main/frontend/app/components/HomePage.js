'use strict';

import React from "react";
import PropTypes from "prop-types";
import Header from "./nav/Header";
import Drawer from "./nav/Drawer";
import AddDialog from "./dialog/AddDialog";
import PanelController from "./panel/PanelController";
import Property from "./util/Property";
import $ from "jquery";

const properties = [
    new Property('repositories_contributed_to', 'repositories contributed to'),
    new Property('own_repositories', 'own repositories'),
    new Property('created_at', 'created at'),
    new Property('issues', 'created issues'),
    new Property('commits_per_day', 'commits per day'),
    new Property('pull_requests', 'pull requests'),
    new Property('followers', 'followers'),
    new Property('total_stars', 'total stars'),
    new Property('commits', 'total commits'),
    new Property('organizations', 'organizations'),
    new Property('repositories_written_in', 'repositories written in'),
    new Property('email', 'email'),
    new Property('location', 'location')
];

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            error: false,
            drawerProperties: properties,
            panelProperties: properties.slice()
        };
        this.addPanel = this.addPanel.bind(this);
        this.changeProperty = this.changeProperty.bind(this);
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
        });
    }

    changeProperty(name, isShown) {
        let props = this.state.panelProperties;
        for (let el of props)
            if (el.hasOwnProperty('name') && el['name'] === name) {
                el['isShown'] = isShown;
                this.setState({
                    panelProperties: props
                });
                return;
            }
    }

    render() {
        return (
            <div className="mdl-layout mdl-js-layout mdl-layout--fixed-header">
                <Header username={this.props.user.name}
                        onLogout={this.props.onLogout}/>
                <Drawer attrs={this.state.drawerProperties}
                        changeProperty={this.changeProperty}/>
                <main className="mdl-layout__content">
                    <PanelController users={this.state.users}
                                     properties={this.state.panelProperties}/>
                    <AddDialog token={this.props.token}
                               addPanel={this.addPanel}/>
                </main>
            </div>
        )
    }
}

HomePage.propTypes = {
    user: PropTypes.object.isRequired,
    onLogout: PropTypes.func.isRequired,
    token: PropTypes.string.isRequired
};

export default HomePage;