'use strict';

const React = require('react');

class MdlCell extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={'mdl-cell mdl-cell--' + this.props.cellWidth + '-col-desktop'}>
                {this.props.children}
            </div>
        )
    }
}

module.exports = MdlCell;