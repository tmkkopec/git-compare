'use strict';

import React from "react";
import PropTypes from "prop-types";
import Property from "../util/Property";
import MdlGrid from "../util/MdlGrid";
import MdlCell from "../util/MdlCell";
import Panel from "./Panel";
const uniqueId = require('lodash/uniqueId');

class PanelController extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <MdlGrid>
                {this.props.users.map((element) => {
                    return (
                        <MdlCell cellWidth={12 / this.props.users.length}
                                 key={uniqueId()}>
                            <Panel data={element}
                                   properties={this.props.properties}
                                   handleClosePanel={this.props.handleClosePanel}/>
                        </MdlCell>
                    )
                })}
            </MdlGrid>
        )
    }
}

PanelController.propTypes = {
    users: PropTypes.array.isRequired,
    properties: PropTypes.arrayOf(PropTypes.instanceOf(Property)).isRequired,
    handleClosePanel: PropTypes.func.isRequired
};

export default PanelController;