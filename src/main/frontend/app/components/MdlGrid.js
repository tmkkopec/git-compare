'use strict';

const React = require('react');

class MdlGrid extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <div className="mdl-grid">{this.props.children}</div>;
    }
}

module.exports = MdlGrid;