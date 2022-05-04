import React, {useState, useEffect} from 'react';
import Input from '../input/Input.jsx';
import Button from "../button/Button";
import classes from './SubscriptionForm.module.css';
import Select from '../select/Select';
import Textarea from '../textarea/Textarea';
import InputBtn from '../button/InputBtn';
import Modal from "../modal/Modal";
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";
import Cookies from 'js-cookie';
import usePostSubscription from "../../../services/usePostSubscription.js";

const SUBSCRIPTION_URL = "api/v1/subscription";

const SubscriptionForm = (props) =>  {

    const [count, setCount] = useState(0);
    const axiosPrivate = useAxiosPrivate();
    const [errMsg, setErrMsg] = useState("");
    const [id, setId] = useState("");
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState(0);
    const [currency, setCurrency] = useState("USD");
    const [averageRating, setAverageRating] = useState(0);
    const [category, setCategory] = useState({});
    const [categoryId, setCategoryId] = useState(0);
    const [categoryList, setCategoryList] = useState([]);
    const [questions, setQuestions] = useState([]);
    const [question, setQuestion] = useState("");

    useEffect( () => {

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

    const onCategoryChange = (e) => {
        setCategory(e.target.value);
        const el = e.target.childNodes[e.target.selectedIndex];
        setCategoryId(Number(el.getAttribute('id')));
    }

     const handleSubmit = async (e) => {
        e.preventDefault();
        setQuestions([...questions, question]);
        const supplier = JSON.parse(Cookies.get("user"));
        try {
            const response = await axiosPrivate.post(
                SUBSCRIPTION_URL,
                {
                    title,
                    description,
                    price,
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
            setId(response.data?.payload.id);
            console.log(questions);
            props.setVisible(false);
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

    const addNewInput = async (e) => {
        e.preventDefault();
        setCount(count+1);
        setQuestions([...questions, question]);
        console.log(questions);
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
                                onChange={(e)=> setTitle(e.target.value)}/>
                        </li>
                        <li>
                            <Input required type="number" id="price" name="price" label="Price" onChange={(e)=> setPrice(e.target.value)}/>
                        </li>
                        <li>
                            <Select
                                name="currency" required
                                label="Currency"
                                onChange={(e)=> setCurrency(e.target.value)}>
                                <option value = "USD">USD </option>
                                <option value = "RUB">RUB </option>
                            </Select>
                        </li>
                        <li>
                            <Select
                                name="currency" required
                                label="Category"
                                onChange={onCategoryChange}>
                                 {categoryList?.map((item) =>
                                    <option key={item.id} id={item.id} value={item.name}>{item.name}</option>)}
                            </Select>
                        </li>
                        <li>
                          <Textarea required id="description" name="description" label="Description" maxLength="120"
                          onChange={(e)=> setDescription(e.target.value)}/>
                        </li>

                    </ul>
                    <Input type="text" name="question" label="Question"
                        onChange={(e)=> {setQuestion(e.target.value);}} />
                    {[...Array(count)].map((i) => <Input type="text" key={i} name="question" label="Question"
                         onChange={(e)=> setQuestion(e.target.value)} />)}

                    {/* <Button  onClick={addNewInput}>
                        Add question
                    </Button> */}
                    <p>{errMsg} </p>
                    <Button>
                        Create Subscription
                    </Button>
                </form>
            </div>
        </Modal>
    );
};

export default SubscriptionForm;