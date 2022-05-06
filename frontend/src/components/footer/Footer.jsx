import React from 'react';
import './footer.css';
import '../../styles/bootstrap.min.css';
import '../../styles/style.css';


const Footer = (props) => {
    return(
        <footer className="footer">
                <div className="container">
                    <div className="row">
                        <div className="col-xl-2 col-lg-2 col-md-3 col-sm-4 col-4 ">
                            <p>Space Co </p>
                        </div>
                    </div>
                </div>
             </footer>
    );
};

export default Footer;