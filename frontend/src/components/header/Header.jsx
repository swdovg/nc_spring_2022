import React from 'react';
import './header.css';
import '../../styles/bootstrap.min.css';
import '../../styles/style.css';
import logo from '../../img/logo.svg';
import facebook from '../../img/facebook.png';
import twitter from '../../img/twitter.png';

const Header = () => {
    return(
        <header className="header">
                <div className="container">
                    <div className="row">
                        <div className="col-xl-4 col-lg-4 col-md-4 col-sm-9 col-9">
                            <img src={logo} alt="Space" className="logo"/>
                        </div>
                        <div className="offset-xl-4 offset-lg-4 offset-md-4 col-xl-2 col-lg-2 col-md-2 d-sm-none d-none d-md-block d-xl-block">
                            <button className="icon">
                                <img src={facebook} alt="facebook"/>
                            </button>
                            <button className="icon">
                                <img src={twitter} alt="twitter"/>
                            </button>
                        </div>
                        <div className="col-xl-2 col-lg-2 col-md-2 col-sm-3 col-3">
                            <a href="sign.html" className="header-sing-link">Sign In</a>
                        </div>
                    </div>
                </div>
             </header>
    );
};

export default Header;