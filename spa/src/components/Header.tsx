import React from "react";
import {HashLink} from "react-router-hash-link";

const logoStyle = {
    width: '250px',
    height: 'auto'
}

const buttonFontSize = {
    fontSize: '22px'
}

const infoFontSize = {
    fontSize: '16px'
}

const Header = () => {
    return (
        <nav className="navbar sticky-top bg-dark">
            <div className="container">
                <div className="col text-start">
                    <HashLink to={'/#home'}>
                        <img src="logo.png" style={logoStyle} alt="logo" />
                    </HashLink>
                </div>
                <div className="col text-center" style={buttonFontSize}>
                    <HashLink to={"/#home"} className="text-white text-decoration-none text-uppercase fw-bold px-2">Home</HashLink>
                    <HashLink to={"/#about"} className="text-white text-decoration-none text-uppercase fw-bold px-2">About us</HashLink>
                </div>
                <div className="col text-end" style={infoFontSize}>
                    <a className="text-white text-decoration-none pe-3" href="tel:+43 5513 87506">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                             className="bi bi-telephone-fill" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M1.885.511a1.745 1.745 0 0 1 2.61.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z"/>
                        </svg>
                        <span className="text-decoration-underline">+43 5513 87506</span>
                    </a>
                    <a className="text-white text-decoration-none" href="mailto:hotel@schwarz.com">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                             className="bi bi-envelope-fill" viewBox="0 0 20 20">
                            <path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757Zm3.436-.586L16 11.801V4.697l-5.803 3.546Z"/>
                        </svg>
                        <span className="text-decoration-underline">hotel@schwarz.com</span>
                    </a>
                </div>
            </div>
        </nav>
    );
}

export default Header;