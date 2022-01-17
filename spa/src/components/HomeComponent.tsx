import React, {Component} from 'react';
import {
    Link
} from "react-router-dom";
import logo from '../images/background.jpg';
import logo2 from '../images/fruhstuck.jpg';
import schwarz from '../images/Schwarz_Thomas.jpg';


class HomeComponent extends Component<{}, {}> {

    render() {
        return (
            <React.Fragment>
                <section className="section2" id="home">
                    <Link to={'/booking'}>
                        <button className="section-heading btn btn-warning btn-lg fw-bold">BOOK NOW!</button>
                    </Link>
                    <div id="image-carousel" className="carousel slide carousel-fade">
                        <div className="carousel-inner">
                            <div className="carousel-item active" data-bs-interval="5000">
                                <img src={logo2} className="d-block h-100" alt="image"/>
                            </div>
                            <div className="carousel-item" data-bs-interval="5000">
                                <img src={logo} className="d-block h-100" alt="image"/>
                            </div>
                        </div>
                        <button className="carousel-control-prev" data-bs-target="#image-carousel" data-bs-slide="prev">
                            <span className="carousel-control-prev-icon"/>
                        </button>
                        <button className="carousel-control-next" data-bs-target="#image-carousel" data-bs-slide="next">
                            <span className="carousel-control-next-icon"/>
                        </button>
                    </div>
                </section>
                <section className="section1 p-5 bg-dark" id="about">
                    <div className="container row mx-auto bg-light p-4 overflow-auto">
                        <div className="col p-5 my-auto">
                            <img src={schwarz} height="auto" width="100%" alt="image"/>
                        </div>
                        <div className="col p-5 fs-5">
                            <span><span className="fw-bold">Moving history and tradition</span>, which can be felt in every corner of our house and radiates security and consistency - and yet does not close itself to the new.
                            <br/><br/>
                            <span className="fw-bold">And the connection to the roots, to the region</span> - on the one hand culinary through the use of products preferably from the Bregenzerwald, on the other hand through the exceptional craftsmanship, which gives our hotel the very special charm.
                            <br/><br/>
                            That which gives you pleasure and a sense of well-being is what we also expect from ourselves.
                            <br/><br/>
                            <span className="fw-bold">Enjoy, feel, experience, live and relax with our claim.</span>
                            <br/><br/><br/>
                            Heartly,
                            <br/>
                            <span className="fst-italic">Thomas Schwarz</span>
                            </span>
                        </div>
                    </div>
                </section>
            </React.Fragment>
        );
    }
}

export default HomeComponent;