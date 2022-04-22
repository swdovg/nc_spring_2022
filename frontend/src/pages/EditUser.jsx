import React, {useContext, useState, useEffect, useRef} from 'react';
import '../styles/edit.css';
import '../styles/style.css';
import '../styles/bootstrap.min.css';
import Button from '../components/UI/button/Button';
import ProfilePhoto from '../components/UI/profilePhoto/ProfilePhoto';
import Select from '../components/UI/select/Select';
import Header from '../components/header/Header.jsx';
import Footer from '../components/footer/Footer.jsx';
import AddressForm from '../components/UI/addressForm/AddressForm.jsx';
import PasswordForm from '../components/UI/passwordForm/PasswordForm.jsx';
import Modal from '../components/UI/modal/Modal.jsx';
import Input from '../components/UI/input/Input.jsx';
import useRefreshToken from "../hook/useRefreshToken.js";
import useAxiosPrivate from "../hook/useAxiosPrivate.js";

const PHONE_REGEX = /^((\+7|7|8)+([0-9]){10})$/;
const PWD_REGEX = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
const NAME_URL = "/api/v1/user/name";
const LOCATION_URL = "/api/v1/user/location";
const CUR_URL = "/api/v1/user/currency";
const ROLES= {
    supplier: "ROLE_SUPPLIER",
    consumer: "ROLE_CONSUMER",
}

const EditUser = () => {
    const axiosPrivate = useAxiosPrivate();
    const [errMsg, setErrMsg] = useState("");

    const [phoneNumber, setPhoneNumber] = useState("");
    const [validPhoneNumber, setValidPhoneNumber] = useState(false);

    const [name, setName] = useState("");
    const [id, setId] = useState("");
    const [defaultLocation, setDefaultLocation] = useState("");
    const [locations, setLocations] = useState({});
    const [currency, setCurrency] = useState("");
    const [role, setRole] = useState("");

    const [password, setPassword] = useState("");
    const [validPassword, setValidPassword] = useState(false);

    const [addressModalVisible, setAddressModalVisible] = useState(false);
    const [passwordModalVisible, setPasswordModalVisible] = useState(false);
    const [subscriptionModalVisible, setSubscriptionModalVisible] = useState(false);

    useEffect(() => {
        const result = PWD_REGEX.test(password);
        setValidPassword(result);
    }, [password] );

    useEffect( () => {
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getInfo = async () => {
            try {
                const response = await axiosPrivate.get("api/v1/user", {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setName(response.data.payload.name);
                isMounted && setId(response.data.payload.id);
                isMounted && setPhoneNumber(response.data.payload.phoneNumber);
                isMounted && setDefaultLocation(response.data.payload.defaultLocation);
                isMounted && setCurrency(response.data.payload.currency);
                isMounted && setRole(response.data.payload.role);

                 response = await axiosPrivate.get("api/v1/location", {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setLocations(response.data.payload);

            } catch(err) {
                console.log(err);
            }
        }
        getInfo();

        return () =>{
            isMounted=false;
            controller.abort();
        }
    }, []);

     const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axiosPrivate.put(
               NAME_URL,
               JSON.stringify({value: name}),
               {
                   headers: {'Content-Type': 'application/json'},
                   withCredentials: true
               }
            )
/*             response = await axiosPrivate.put(
                LOCATION_URL,
                {
                    location: JSON.stringify(defaultLocation),
                    userId: id
                },
                {
                    headers: {'Content-Type': 'application/json'},
                    withCredentials: true
                }
             ) */
            response = await axiosPrivate.put(
               CUR_URL,
               JSON.stringify({value: currency}),
               {
                   headers: {'Content-Type': 'application/json'},
                   withCredentials: true
               }
            )
             console.log(response.data);
        }
        catch(err) {
            if (!err?.response)
                setErrMsg("No server response");
            else if (err.response?.status===400)
                setErrMsg("Invalid Data");
            else
                setErrMsg("Submission Failed");
            //errRef.current.focus();
        }
        }

    return (
        <div>
            <Header />
            <div className="container">
                <h1 className="edit-heading">Edit your profile</h1>
                <hr className="line" />

                <div className="row edit-cont">
                    <div className="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
                        <ProfilePhoto />
                        {role===ROLES.consumer
                            ?
                                <Button onClick={()=>setAddressModalVisible(true)}>
                                    Add new address
                                </Button>
                            :
                                <Button onClick={()=>setSubscriptionModalVisible(true)}>
                                    Add New Subscription
                                </Button>
                        }
                    </div>
                    <div className="offset-lg-2 col-xl-5 col-lg-5 col-md-12 col-sm-12 col-12">
                        <form method="post" onSubmit={handleSubmit}>
                            <ul>
                                <li>
                                    <Input
                                        type="text"
                                        id="name"
                                        label="Name"
                                        onChange={(e)=> setName(e.target.value)}
                                        value = {name}/>
                                </li>
                                <li>
                                <Input
                                    type="phone"
                                    id="phone"
                                    readonly
                                    value = {phoneNumber}
                                    label={"Phone Number"}/>
                                </li>
                                <li>
                                    <Select
                                        defaultValue={defaultLocation}
                                        onChange={(e)=> setDefaultLocation(e.target.value)}
                                        options={[
                                            {value:"1", name:"1"},
                                            {value:"2", name:"2"}
                                        ]}
                                        label="Main Address"
                                    />
                                </li>
                                <li>
                                    <Select
                                        required="required"
                                        defaultValue={currency}
                                        options={[
                                            {value:"USD", name:"USD"},
                                            {value:"RUB", name:"RUB"}
                                        ]}
                                        label="Currency"
                                        onChange={(e)=> setCurrency(e.target.value)}
                                    />
                                </li>
                            </ul>
                            <Button className="inline-btn" >Save changes</Button>
                        </form>
                        <Button className="inline-btn" onClick={()=>setPasswordModalVisible(true)}>Change password</Button>
                    </div>
                </div>
            </div>
            <Footer />
            <Modal visible={addressModalVisible} setVisible={setAddressModalVisible}>
                <AddressForm />
            </Modal>
            <Modal visible={passwordModalVisible} setVisible={setPasswordModalVisible}>
                <PasswordForm />
            </Modal>
        </div>
        );
    };

export default EditUser;