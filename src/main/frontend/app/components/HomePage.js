'use strict';

import React from "react";
import PropTypes from "prop-types";
import Header from "./nav/Header";
import Drawer from "./nav/Drawer";
import AddDialog from "./dialog/AddDialog";
import PanelController from "./panel/PanelController";
import Property from "./util/Property";
const uniqueId = require('lodash/uniqueId');

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
        this.removePanel = this.removePanel.bind(this);
    }

    addPanel(username) {
        // const url = "http://localhost:8080/users/" + username;
        const self = this;
        // $.ajax({
        //     url: url,
        //     success: function (data) {
        //         self.setState({
        //             error: false,
        //             users: self.state.users.concat([data])
        //         });
        //     },
        //     error: function (jqXHR, textStatus, errorThrown) {
        //         self.setState({
        //             error: errorThrown
        //         })
        //     }
        // });
        const data = {
            "repositories_contributed_to": 39,
            "own_repositories": 9,
            "created_at": "2011-05-18T14:51:21Z",
            "login": "odersky",
            "issues": 91,
            "commits_per_day": 6.8844537815126055,
            "pull_requests": 711,
            "followers": 1520,
            "total_stars": 125,
            "avatar_url": "https://avatars1.githubusercontent.com/u/795990?v=3",
            "repositories_written_in": {
                "CSS": 2,
                "Scala": 6,
                "JavaScript": 1
            },
            "html_url": "https://github.com/odersky",
            "name": null,
            "organizations": [],
            "commits": 36047,
            "location": null,
            "email": null
        };
        data._id = uniqueId();
        self.setState({
            error: false,
            users: self.state.users.concat([data])
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

    removePanel(id) {
        let users = this.state.users.slice();
        for (let i = 0; i < users.length; i++)
            if (users[i]._id === id) {
                users.splice(i, 1);
                this.setState({
                    users: users
                });
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
                                     properties={this.state.panelProperties}
                                     handleClosePanel={this.removePanel}/>
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