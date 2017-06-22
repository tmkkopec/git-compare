'use strict';

const React = require('react');
const uniqueId = require('lodash/uniqueId');

class PanelRow extends React.Component {
    constructor(props) {
        super(props);
        this.parseValue = this.parseValue.bind(this);
    }

    parseValue(val) {
        if (val instanceof Array)
            return val.length > 0 ?
                val.map((element) => element.name).join(', ') :
                'No info available';
        else if (val instanceof Object) {
            let renderValue = '';
            Object.keys(val).forEach((key) => {
                renderValue += key + ': ' + JSON.stringify(val[key], 4) + '\n';
            });
            return renderValue;
        }
        else
            return val !== null ? String(val) : 'No info available';
    }

    render() {
        return (
            <tr>
                <td className="mdl-data-table__cell--non-numeric">
                    <p className="mdl-typography--body-2">{this.props.propertyName}</p>
                </td>
                <td>
                    <p>{this.parseValue(this.props.propertyValue)}</p>
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
                {this.props.properties
                    .filter((element) => {
                        return element.isShown
                    })
                    .map((element) => {
                        return (
                            <PanelRow propertyName={element.name}
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