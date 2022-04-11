import React from 'react';
import './header.css';
import '../../styles/bootstrap.min.css';
import '../../styles/style.css';
import logo from '../../img/logo.svg';
import facebook from '../../img/facebook.png';
import twitter from '../../img/twitter.png';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";

const Header = () => {
    return(
        <header className="header">
                <div className="container">
                    <div className="row">
                        <div className="col-xl-4 col-lg-4 col-md-5 col-sm-6 col-6">
                        <Link to="/main"><img src={logo} alt="Space" className="logo"/></Link>
                        </div>
                        <div className="offset-xl-2 offset-lg-2 col-xl-3 col-lg-3 col-md-3 d-sm-none d-none d-md-block d-xl-block">
                            <button className="icon">
                                <img src={facebook} alt="facebook"/>
                            </button>
                            <button className="icon">
                                <img src={twitter} alt="twitter"/>
                            </button>
                        </div>
                        <div className="col-xl-3 col-lg-3 col-md-4 col-sm-6 col-6">
                            <Link to="/customer-profile" className="header-sing-link">My profile</Link>
                            <Link to="/sign" className="header-sing-link">Sign In</Link>
                        </div>
                    </div>
                </div>
             </header>
    );
};

export default Header;