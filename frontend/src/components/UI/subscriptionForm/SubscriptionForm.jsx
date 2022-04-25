import React, {useState, useEffect} from 'react';
import Input from '../input/Input.jsx';
import Button from "../button/Button";
import classes from './SubscriptionForm.module.css';
import Select from '../select/Select';
import Textarea from '../textarea/Textarea';
import InputBtn from '../button/InputBtn';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";
import Cookies from 'js-cookie';

const SUBSCRIPTION_URL = "api/v1/subscription"

const SubscriptionForm = (...props) =>  {

    const [count, setCount] = useState(0);

    function addNewInput (e) {
        e.preventDefault();
        setCount(count+1);
    }

    const axiosPrivate = useAxiosPrivate();
    const [errMsg, setErrMsg] = useState("");

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState(0);
    const [currency, setCurrency] = useState({});
    const [averageRating, setAverageRating] = useState(0);
    const [category, setCategory] = useState({});
    const [categoryId, setCategoryId] = useState(0);
    const [categoryList, setCategoryList] = useState([]);

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
        const supplier = JSON.parse(Cookies.get("user"));
        console.log(description);
        const subInfo ={
            title,
            description,
            price,
            averageRating,
            category: {
                id: categoryId,
                name: category,
                parentId: 0
            }
         };
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
        <h2 className={classes.heading}>Manage Subscription</h2>
        <hr className={classes.line} />
        <form onSubmit = {handleSubmit} className={classes.form}>
            <ul className={classes.form_inputs}>
                <li>
                    <Input required type="text" id="subscription-name" name="subscription-name" label="Name" onChange={(e)=> setTitle(e.target.value)}/>
                </li>
                <li>
                    <Input required type="number" id="price" name="price" label="Price" onChange={(e)=> setPrice(e.target.value)}/>
                </li>
                <li>
                    <Select
                        name="currency" required="required"
                        defaultValue="Currency"
                        label="Currency"
                        onChange={(e)=> setCurrency(e.target.value)}>
                        <option>USD </option>
                        <option>RUB </option>
                    </Select>
                </li>
                <li>
                    <Select
                        name="currency" required="required"
                        defaultValue="Category"
                        label="Category"
                        onChange={onCategoryChange}>
                         {categoryList?.map((loc) =>
                            <option key={loc.id} id={loc.id} value={loc.name}>{loc.name}</option>)}
                    </Select>
                </li>
                <li>
                  <Textarea required id="description" name="description" label="Description" maxlength="120"
                  onChange={(e)=> setDescription(e.target.value)}/>
                </li>
            </ul>
            {[...Array(count)].map(() => <Input type="text" id={count} name="subscription-question" label="Question"/>)}

            <Button onClick={addNewInput}>
                Add question
            </Button>

            <Button>
                Save changes
            </Button>
        </form>
    </div>
    );
};

export default SubscriptionForm;