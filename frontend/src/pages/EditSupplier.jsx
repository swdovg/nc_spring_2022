import React, {useContext, useState} from 'react';
import '../styles/edit.css';
import '../styles/style.css';
import '../styles/bootstrap.min.css';
import Button from '../components/UI/button/Button';
import ProfilePhoto from '../components/UI/profilePhoto/ProfilePhoto';
import Select from '../components/UI/select/Select';
import Header from '../components/header/Header.jsx';
import Footer from '../components/footer/Footer.jsx';
import SubscriptionForm from '../components/UI/subscriptionForm/SubscriptionForm.jsx';
import Modal from '../components/UI/modal/Modal.jsx';
import Input from '../components/UI/input/Input.jsx';


const EditSupplier = () => {

    const [modalVisible, setModalVisible] = useState(false);

    return (
        <div>
            <Header />
            <div className="container">
                <h1 className="edit-heading">Edit your profile</h1>
                <hr className="line" />

                <div className="row edit-cont">
                    <div className="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
                        <ProfilePhoto />
                        <Button onClick={()=>setModalVisible(true)}>
                            Manage Subscription
                        </Button>
                    </div>
                    <div className="offset-lg-2 col-xl-5 col-lg-5 col-md-12 col-sm-12 col-12">
                        <form action="edit-form" method="post">
                            <ul>
                                <li>
                                  <Input type="text" id="company-name" name="company-name" label="Company Name"/>
                                </li>
                                <li>
                                  <Input type="phone" id="company-phone" name="company-phone" label={"Phone Number"}/>
                                </li>
                                <li>
                                  <Input type="text" id="company-location" name="company-location" label={"Location"}/>
                                </li>
                                <li>
                                  <Input className="edit-form-input" type="password" id="password" name="user_password" label="Password"/>
                                </li>
                            </ul>
                            <Button className="inline-btn">Change password</Button>
                            <Button className="inline-btn">Save changes</Button>
                        </form>

                    </div>
                </div>
            </div>
            <Footer />
            <Modal visible={modalVisible} setVisible={setModalVisible}>
                <SubscriptionForm />
            </Modal>
        </div>
        );
    };

export default EditSupplier;