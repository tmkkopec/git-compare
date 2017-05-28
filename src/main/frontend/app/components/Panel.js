'use strict';

const React = require('react');
const uniqueId = require('lodash/uniqueId');

class PanelRow extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        let val = this.props.propertyValue;
        return (
            <tr>
                <td className="mdl-data-table__cell--non-numeric">
                    <p className="mdl-typography--body-2">{this.props.propertyName}</p>
                </td>
                <td>
                    <p>{val !== null ? String(val) : 'No info available'}</p>
                </td>
            </tr>
        )
    }
}

class Panel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const userData = this.props.data;
        let name = '',
            surname = '',
            login = userData.login;

        if (userData.name !== null) {
            let split = userData.name.split(' ');
            name = split[0];
            surname = split[1] !== undefined ? split[1] : '';
        }

        return (
            <table className="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                <tbody>
                <tr>
                    <td>
                        <a className="mdl-typography--body-2" href={userData.html_url}>
                            {name + ' "' + login + '" ' + surname}
                        </a>
                    </td>
                    <td className="mdl-data-table__cell--non-numeric">
                        <img className="material-icons" src={userData.avatar_url}/>
                    </td>
                </tr>
                {this.props.properties.map((element) => {
                    return (
                        <PanelRow propertyName={element.value}
                                  propertyValue={userData[element.key]}
                                  key={uniqueId()}/>
                    )
                })}
                </tbody>
            </table>
        )
    }
}

export default Panel;