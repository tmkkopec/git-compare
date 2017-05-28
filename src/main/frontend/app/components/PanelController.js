'use strict';

const React = require('react');
import MdlGrid from "./MdlGrid";
import MdlCell from "./MdlCell";
import Panel from "./Panel";
const uniqueId = require('lodash/uniqueId');

class PanelController extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <MdlGrid>
                {this.props.users.map((element, index) => {
                    return (
                        <MdlCell cellWidth={12 / this.props.users.length}
                                 key={uniqueId()}>
                            <Panel data={element}
                                   properties={this.props.properties}/>
                        </MdlCell>
                    )
                })}
            </MdlGrid>
        )
    }
}

export default PanelController;