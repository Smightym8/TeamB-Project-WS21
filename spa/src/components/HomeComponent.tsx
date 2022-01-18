import React, {Component} from 'react';
import {Link} from "react-router-dom";

import hotel from '../images/hotel.jpg';
import pool from '../images/pool.jpg';
import sauna from '../images/sauna.jpg';
import apartment1 from '../images/apartment1.jpg';
import apartment2 from '../images/apartment2.jpg';
import room from '../images/room.jpg';
import thomas from '../images/thomas.jpg';

class HomeComponent extends Component<{}, {}> {

    render() {
        return (
            <React.Fragment>
                <section className="section2" id="home">
                    <Link to={'/booking'}>
                        <button className="section-heading btn btn-primary btn-lg fw-bold">BOOK NOW!</button>
                    </Link>
                    <div id="image-carousel" className="carousel slide carousel-fade h-100 overflow-hidden" data-bs-ride="carousel" data-bs-interval="6000" data-interval="true">
                        <div className="carousel-inner h-100">
                            <div className="carousel-item h-100 active">
                                <img src={hotel} className="d-block pic" alt="image"/>
                            </div>
                            <div className="carousel-item h-100">
                                <img src={pool} className="d-block pic" alt="image"/>
                            </div>
                            <div className="carousel-item h-100">
                                <img src={sauna} className="d-block pic" alt="image"/>
                            </div>
                            <div className="carousel-item h-100">
                                <img src={apartment1} className="d-block pic" alt="image"/>
                            </div>
                            <div className="carousel-item h-100">
                                <img src={apartment2} className="d-block pic" alt="image"/>
                            </div>
                            <div className="carousel-item h-100">
                                <img src={room} className="d-block pic" alt="image"/>
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
                            <img src={thomas} height="auto" width="100%" alt="image"/>
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