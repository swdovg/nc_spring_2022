import React, {useState, useEffect} from 'react';
import Input from '../input/Input.jsx';
import Button from "../button/Button";
import classes from '../subscriptionForm/SubscriptionForm.module.css';
import Select from '../select/Select';
import Textarea from '../textarea/Textarea';
import InputBtn from '../button/InputBtn';
import '../../../styles/bootstrap.min.css';
import SubscriptionForm from '../subscriptionForm/SubscriptionForm';
import Cookies from 'js-cookie';
import Modal from '../modal/Modal.jsx';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";

const SUBSCRIPTION_URL = "api/v1/subscription";

const EditSubscriptionModal = (props) => {

    const [count, setCount] = useState(0);
    const axiosPrivate = useAxiosPrivate();
    const [errMsg, setErrMsg] = useState("");
    const [id, setId] = useState(props.id);
    const [title, setTitle] = useState(props.title);
    const [description, setDescription] = useState(props.description);
    const [price, setPrice] = useState(props.price);
    const [currency, setCurrency] = useState(props.currency);
    const [averageRating, setAverageRating] = useState(0);
    const [category, setCategory] = useState({});
    const [categoryId, setCategoryId] = useState(0);
    const [categoryList, setCategoryList] = useState([]);
    const [question, setQuestion] = useState("");

    const onCategoryChange = (e) => {
        setCategory(e.target.value);
        const el = e.target.childNodes[e.target.selectedIndex];
        setCategoryId(Number(el.getAttribute('id')));
    }

    const addNewInput = async (e) => {
        e.preventDefault();
        setCount(count+1);
        console.log(question);
    }

    useEffect( () => {
        setTitle(props.title);
        setDescription(props.description);
        setPrice(props.price);
        setId(props.id);
        setCurrency(props.currency);
        setCategory(props.category);

        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getCategories = async () => {
            try {
                let response = await axiosPrivate.get("api/v1/category/0", {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setCategoryList(response.data.payload);
            } catch(err) {
                console.log(err);
            }
        }
        getCategories();
        return () =>{
            isMounted=false;
            controller.abort();
        }
    }, []);

     const handleSubmit = async (e) => {
        e.preventDefault();
        const supplier = JSON.parse(Cookies.get("user"));
        try {
            const response = await axiosPrivate.put(
                SUBSCRIPTION_URL,
                {
                    id,
                    title,
                    description,
                    price,
                    currency,
                    averageRating,
                    category: {
                     id: categoryId,
                     name: category,
                     parentId: 0
                    },
                    supplier,
                    ordered:true
                 },
                {
                    headers: {'Content-Type': 'application/json'},
                    withCredentials: true
                }
             );
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
        <Modal visible={props.visible} setVisible={props.setVisible}>
            <div>
                <h2 className={classes.heading}>Add New Subscription</h2>
                <hr className={classes.line} />
                <form onSubmit = {handleSubmit} className={classes.form}>
                    <ul className={classes.form_inputs}>
                        <li>
                            <Input required
                                type="text"
                                id="subscription-title"
                                name="subscription-title"
                                label="Title"
                                onChange={(e)=> setTitle(e.target.value)}
                                value={title}/>
                        </li>
                        <li>
                            <Input required type="number" id="price" name="price" label="Price" onChange={(e)=> setPrice(e.target.value)}
                                value={price}/>
                        </li>
                        <li>
                            <Select
                                name="currency" required
                                defaultValue={currency}
                                label="Currency"
                                onChange={(e)=> setCurrency(e.target.value)}>
                                <option value = "USD">USD </option>
                                <option value = "RUB">RUB </option>
                            </Select>
                        </li>
                        <li>
                            <Select
                                name="Category" required
                                defaultValue={category?.name}
                                label="Category"
                                onChange={onCategoryChange}>
                                 {categoryList?.map((loc) =>
                                    <option key={loc.id} id={loc.id} value={loc.name}>{loc.name}</option>)}
                            </Select>
                        </li>
                        <li>
                          <Textarea required id="description" name="description" label="Description" maxLength="120"
                          onChange={(e)=> setDescription(e.target.value)} value={description}/>
                        </li>
                    </ul>
                    {[...Array(count)].map((i) => <Input type="text" key={i} name="question" label="Question"
                         onChange={(e)=> setQuestion(e.target.value)} />)}

                    <Button onClick={addNewInput}>
                        Add question
                    </Button>

                    <Button>
                        Save changes
                    </Button>
                </form>
            </div>
        </Modal>

    );
};

export default EditSubscriptionModal;