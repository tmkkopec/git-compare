'use strict';

import React from "react";
import Header from "./Header";
import Drawer from "./Drawer";
import AddDialog from "./AddDialog";

const filteringAttributes = [
    "name",
    "surname",
    "commits",
    "pull requests"
];

class HomePage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="mdl-layout mdl-js-layout mdl-layout--fixed-header mdl-layout--fixed-drawer">
                <Header username={this.props.user.name} onLogout={this.props.onLogout}/>
                <Drawer attrs={filteringAttributes}/>
                <main className="mdl-layout__content">
                    <div className="page">
                        <AddDialog token={this.props.token}/>
                    </div>
                </main>
            </div>
        )
    }
}

export default HomePage;