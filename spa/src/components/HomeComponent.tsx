import React, {Component} from 'react';
import {
    Link
} from "react-router-dom";


class HomeComponent extends Component<{}, {}> {

    render() {
        return (
            <div>
                <h1>Welcome to Hotel Schwarz!</h1>

                <h2>Book your next holiday now</h2>

                <Link to={'/choosedates'}>
                    <button className="btn btn-primary">Book now</button>
                </Link>
            </div>
        );
    }
}

export default HomeComponent;