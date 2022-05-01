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
import SubscriptionForm from '../components/UI/subscriptionForm/SubscriptionForm.jsx';
import Modal from '../components/UI/modal/Modal.jsx';
import Input from '../components/UI/input/Input.jsx';
import useAxiosPrivate from "../hook/useAxiosPrivate.js";
import Cookies from 'js-cookie';
import usePostSubscription from "../services/usePostSubscription.js";


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

    const [name, setName] = useState("");
    const [userId, setId] = useState("");
    const [defaultLocation, setDefaultLocation] = useState("");
    const [locationId, setLocationId] = useState();
    const [locations, setLocations] = useState([]);
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
        const user = JSON.parse(Cookies.get("user"));
        setName(user.name);
        setDefaultLocation(user.defaultLocation?.location);
        setLocationId(user.defaultLocation?.id);
        setPhoneNumber(user.phoneNumber);
        setRole(user.role);
        setCurrency(user.currency);
        setId(user.id);

        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getInfo = async () => {
            try {
                 const response = await axiosPrivate.get(
                 LOCATION_URL,
                 {
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

    const onLocationChange = (e) => {
        setDefaultLocation(e.target.value);
        const el = e.target.childNodes[e.target.selectedIndex];
        setLocationId(Number(el.getAttribute('id')));
    }

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
            const curResponse = await axiosPrivate.put(
                CUR_URL,
                JSON.stringify({value: currency}),
                {
                   headers: {'Content-Type': 'application/json'},
                   withCredentials: true
                }
            )
             const locResponse = await axiosPrivate.put(
                 LOCATION_URL,
                 JSON.stringify({id: locationId, location: defaultLocation, userId}),
                 {
                     headers: {'Content-Type': 'application/json'},
                     withCredentials: true
                 }
            )

        }
        catch(err) {
            if (!err?.response)
                setErrMsg("No server response");
            else if (err.response?.status===400)
                setErrMsg("Invalid Data");
            else
                setErrMsg("Submission Failed");
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
                                {locations?.length>0
                                ?
                                    <Select
                                        defaultValue={defaultLocation}
                                        onChange={onLocationChange}
                                        label="Main Address">
                                    {locations.map((loc) =>
                                        <option key={loc.id} id={loc.id} value={loc.location}>{loc.location}</option>)}
                                    </Select>
                                :
                                    <Input
                                        type="text"
                                        id="defaultLocation"
                                        value = {defaultLocation}
                                        label={"Main Address"}
                                        onChange={(e)=> setDefaultLocation(e.target)}/>
                                }
                                </li>
                                <li>
                                    <Select
                                        required="required"
                                        defaultValue={currency}
                                        label="Currency"
                                        onChange={(e)=> setCurrency(e.target.value)}>
                                        <option value="USD"> USD</option>
                                        <option value="RUB"> RUB</option>
                                    </Select>
                                </li>
                            </ul>
                            <p> {errMsg}</p>
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
            <Modal visible={subscriptionModalVisible} setVisible={setSubscriptionModalVisible}>
                <SubscriptionForm submitFunction={usePostSubscription}/>
            </Modal>
            <Modal visible={passwordModalVisible} setVisible={setPasswordModalVisible}>
                <PasswordForm />
            </Modal>
        </div>
        );
    };

export default EditUser;