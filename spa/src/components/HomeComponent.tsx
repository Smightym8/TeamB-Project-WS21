import React, {Component} from 'react';

class HomeComponent extends Component {
    render() {
        return (
            <div>
                <div className="jumbotron">
                    <h1 className="display-4">Hello, world!</h1>
                    <button className="btn btn-success"> Click me! </button>
                </div>
            </div>
        );
    }
}

export default HomeComponent;