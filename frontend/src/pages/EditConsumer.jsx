import React, {useContext, useState} from 'react';
import '../styles/edit.css';
import '../styles/style.css';
import '../styles/bootstrap.min.css';
import Button from '../components/UI/button/Button';
import ProfilePhoto from '../components/UI/profilePhoto/ProfilePhoto';
import Select from '../components/UI/select/Select';
import Header from '../components/header/Header.jsx';
import Footer from '../components/footer/Footer.jsx';
import AddressForm from '../components/UI/addressForm/AddressForm.jsx';
import Modal from '../components/UI/modal/Modal.jsx';
import Input from '../components/UI/input/Input.jsx';


const EditConsumer = () => {

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
                            Manage address
                        </Button>
                    </div>
                    <div className="offset-lg-2 col-xl-5 col-lg-5 col-md-12 col-sm-12 col-12">
                        <form action="edit-form" method="post">
                            <ul>
                                <li>
                                  <Input type="text" id="first-name" name="first-name" label="First Name"/>
                                </li>
                                <li>
                                  <Input className="edit-form-input" type="text" id="last-name" name="last-name" label={"Last Name"}/>
                                </li>
                                <li>
                                  <Input type="phone" id="phone" name="phone" label={"Phone Number"}/>
                                </li>
                                <Select
                                    defaultValue="Main Address"
                                    options={[
                                        {value:"1", name:"1"},
                                        {value:"2", name:"2"}
                                    ]}
                                    label="Main Address"
                                />
                                <Select
                                    name="currency" required="required"
                                    defaultValue="Currency"
                                    options={[
                                        {value:"1", name:"USD"},
                                        {value:"2", name:"RUB"}
                                    ]}
                                    label="Currency"
                                />
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
                <AddressForm />
            </Modal>
        </div>
        );
    };

export default EditConsumer;