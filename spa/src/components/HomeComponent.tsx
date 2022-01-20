import React from 'react';
import {Link} from "react-router-dom";
import {HashLink} from "react-router-hash-link";

import hotel from '../images/hotel.jpg';
import pool from '../images/pool.jpg';
import sauna from '../images/sauna.jpg';
import apartment1 from '../images/apartment1.jpg';
import apartment2 from '../images/apartment2.jpg';
import room from '../images/room.jpg';
import thomas from '../images/thomas.jpg';

const HomeComponent = () => {

    const logoStyle = {
        width: '250px',
        height: 'auto'
    };

    const buttonContainerStyle = {
      fontSize: '22px'
    };

    const phoneContainerStyle = {
        fontSize: '16px'
    }

    const headerStyle = {
        boxShadow: '0 0 10px rgba(0, 0, 0, 0.5)'
    };

    return (
        <React.Fragment>
            <nav style={headerStyle} className="navbar sticky-top bg-dark">
                <div className="container">
                    <div className="col text-start">
                        <HashLink to={'/#home'}>
                            <img src="logo.png" style={logoStyle} alt="logo" />
                        </HashLink>
                    </div>
                    <div className="col text-center" style={buttonContainerStyle}>
                        <HashLink className="text-white text-decoration-none text-uppercase fw-bold px-2"
                           to={"/#home"}>Home</HashLink>
                        <HashLink className="text-white text-decoration-none text-uppercase fw-bold px-2"
                           to={"/#about"}>About us</HashLink>
                    </div>
                    <div className="col text-end" style={phoneContainerStyle}>
                        <a className="text-white text-decoration-none pe-3" href="tel:+43 5513 87506">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                                 className="bi bi-telephone-fill" viewBox="0 0 20 20">
                                <path fill-rule="evenodd"
                                      d="M1.885.511a1.745 1.745 0 0 1 2.61.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z"/>
                            </svg>
                            <span className="text-decoration-underline">+43 5513 87506</span>
                        </a>
                        <a className="text-white text-decoration-none" href="mailto:hotel@schwarz.com">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                                 className="bi bi-envelope-fill" viewBox="0 0 20 20">
                                <path
                                    d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757Zm3.436-.586L16 11.801V4.697l-5.803 3.546Z"/>
                            </svg>
                            <span className="text-decoration-underline">hotel@schwarz.com</span>
                        </a>
                    </div>
                </div>
            </nav>

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

export default HomeComponent;