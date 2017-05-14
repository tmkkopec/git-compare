'use strict';

import React from "react";

class DynamicList extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="card card-2" style={{display: (this.props.isVisible ? 'inline-block' : 'none')}}>
                <ul className="mdl-list">
                    {this.props.list.map((item, index) =>
                        <li key={item.login} className="mdl-list__item"
                            onClick={() => this.props.handleUserSelect(item.login)}>
                            <img className="material-icons" src={item.avatar_url}/>
                            {item.login}
                        </li>
                    )}
                </ul>
            </div>
        )
    }
}

export default DynamicList;