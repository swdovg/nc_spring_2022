import React, {useState, useEffect} from 'react';
import Input from '../input/Input.jsx';
import Button from "../button/Button";
import classes from '../subscriptionForm/SubscriptionForm.module.css';
import Select from '../select/Select';
import Textarea from '../textarea/Textarea';
import InputBtn from '../button/InputBtn';
import InputFile from '../inputFile/InputFile';
import '../../../styles/bootstrap.min.css';
import SubscriptionForm from '../subscriptionForm/SubscriptionForm';
import Cookies from 'js-cookie';
import Modal from '../modal/Modal.jsx';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";

const SUBSCRIPTION_URL = "api/v1/subscription";
const QUESTION_URL = "/api/v1/form/question";

const EditSubscriptionModal = (props) => {
    const POST_IMG_URL = `/api/v1/image/subscription?subscriptionId=${props.id}`;

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
    const [image, setImage] = useState();
    const supplier = JSON.parse(Cookies.get("user"));

    const onCategoryChange = (e) => {
        setCategory(e.target.value);
        const el = e.target.childNodes[e.target.selectedIndex];
        setCategoryId(Number(el.getAttribute('id')));
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

    const putSubscription = async () => {
        try {
            const response = await axiosPrivate.put(
                SUBSCRIPTION_URL,
                {
                    id,
                    title,
                    description,
                    price,
                    currency,
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
            setId(response.data.payload?.id);
            console.log(id);
        }
        catch(err) {
            setErrMsg(err.response.message);
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (id === undefined){
            putSubscription();
        }

        const formData = new FormData();
        formData.append("image", image);
        const POST_IMG_URL = `/api/v1/image/subscription?subscriptionId=${id}`;
        try {
            await axiosPrivate.post(
                POST_IMG_URL,
                formData,
                {
                    headers: {"Content-type": "multipart/form-data"},
                    withCredentials: true
                }
            )
            setImage({});
        }
        catch (err) {
            if (err?.response)
                setErrMsg(err.message)
            else if (err.response?.status === 500)
                setErrMsg("Maximum size is 1MB");
        }
        if (question != "") {
            try {
                const questionResponse = await axiosPrivate.post(
                    QUESTION_URL,
                    {
                        subscriptionId: id,
                        question: question
                     },
                    {
                        headers: {'Content-Type': 'application/json'},
                        withCredentials: true
                    }
                 );
            }
            catch(err) {
                setErrMsg(err.response.message);
            }
        }

        props.setVisible(false);
    }

    const addNewInput = async (e) => {
        e.preventDefault();
        setCount(count+1);
        if (id===undefined){
            putSubscription();
        }
        try {
            const questionResponse = await axiosPrivate.post(
                "/api/v1/form/question",
                {
                    subscriptionId: id,
                    question: question
                 },
                {
                    headers: {'Content-Type': 'application/json'},
                    withCredentials: true
                }
             );
        }
        catch(err) {
            setErrMsg("Something went wrong. Try again, please");
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
                        <li>
                            <label className={classes.file_upload}>
                                Choose photo
                            <input className={classes.input_file}  type="file" onChange={(e) => setImage(e.target.files[0])}/>
                            </label>
                        </li>
                    </ul>
                    <Input type="text" name="question" label="Question (optional)"
                        onChange={(e)=> {setQuestion(e.target.value);}} />
                    {[...Array(count)].map((i) => <Input type="text" key={i} name="question" label="Question"
                         onChange={(e)=> setQuestion(e.target.value)} />)}
                    <p>{errMsg} </p>
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