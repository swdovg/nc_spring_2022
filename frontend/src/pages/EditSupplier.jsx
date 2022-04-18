import React, {useContext, useState, useEffect} from 'react';
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
import useRefreshToken from "../hook/useRefreshToken.js"
import useAxiosPrivate from "../hook/useAxiosPrivate.js"
import { useNavigate, useLocation } from "react-router-dom";

const PHONE_REGEX = /^((\+7|7|8)+([0-9]){10})$/;
const PWD_REGEX = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;

const EditSupplier = () => {
    const axiosPrivate = useAxiosPrivate();

    const [phoneNumber, setPhoneNumber] = useState("");
    const [validPhoneNumber, setValidPhoneNumber] = useState(false);

    const [name, setName] = useState("");
    const [defaultLocation, setDefaultLocation] = useState("");
    const [currency, setCurrency] = useState("");

    const [password, setPassword] = useState("");
    const [validPassword, setValidPassword] = useState(false);

    const [modalVisible, setModalVisible] = useState(false);

    useEffect(() => {
        const result = PHONE_REGEX.test(phoneNumber);
        setValidPhoneNumber(result);
    }, [phoneNumber] );

    useEffect(() => {
        const result = PWD_REGEX.test(password);
        setValidPassword(result);
    }, [password] );

    useEffect( () => {
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getUserInfo = async () => {
            try {
                const response = await axiosPrivate.get("api/v1/user", {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setName(response.data.payload.name);
                isMounted && setPhoneNumber(response.data.payload.phoneNumber);
                isMounted && setDefaultLocation(response.data.payload.defaultLocation);
                isMounted && setCurrency(response.data.payload.currency);
                isMounted && setPassword(response.data.payload.name);

            } catch(err) {
                console.log(err);
            }
        }
        getUserInfo();

        return () =>{
            isMounted=false;
            controller.abort();
        }
    }, []);

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
                            Add New Subscription
                        </Button>
                    </div>
                    <div className="offset-lg-2 col-xl-5 col-lg-5 col-md-12 col-sm-12 col-12">
                        <form method="post">
                            <ul>
                                <li>
                                    <Input
                                        type="text"
                                        id="name"
                                        label="Name"
                                        onChange={(e)=> setName(e.target.value)}
                                        placeholder = {name}/>
                                </li>
                                <li>
                                    <Input
                                        type="phone"
                                        id="user-phone"
                                        onChange={(e)=> setPhoneNumber(e.target.value)}
                                        label={"Phone Number"}
                                        aria-invalid={validPhoneNumber ? "false" : "true"}
                                        placeholder = {phoneNumber}/>
                                </li>
                                <li>
                                    <Input
                                        type="text"
                                        id="user-location"
                                        label={"Location"}
                                        onChange={(e)=> setDefaultLocation(e.target.value)}
                                        placeholder = {defaultLocation}
                                        />
                                </li>
                                 <li>
                                    <Select
                                        required="required"
                                        defaultValue={currency}
                                        options={[
                                            {value:"1", name:"USD"},
                                            {value:"2", name:"RUB"}
                                        ]}
                                        label="Currency"
                                        onChange={(e)=> setCurrency(e.target.value)}
                                    />
                                </li>
                                <li>
                                    <Input
                                        type="password"
                                        id="password"
                                        label="Password"
                                        onChange={(e)=> setPassword(e.target.value)}
                                        required
                                        aria-invalid={validPassword ? "false" : "true"}
                                    />
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