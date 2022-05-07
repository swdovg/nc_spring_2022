import React, {useEffect, useState} from 'react';
import cl from './ProductCard.module.css';
import '../../../styles/bootstrap.min.css';
import Button from '../button/Button';
import Modal from '../modal/Modal';
import Input from '../input/Input';
import card_img from '../../../img/profile_img.png';
import Cookies from 'js-cookie';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";

const ProductCard = (props) => {

    const axiosPrivate = useAxiosPrivate();
    const [questions, setQuestions] = useState([]);
    const [orderId, setOrderId] = useState();
    const [i, setI] = useState(0);
    const [question, setQuestion] = useState({});
    const [image, setImage] = useState(card_img);
    const [questionModalVisible, setQuestionModalVisible] = useState(false);
    const [answer, setAnswer] = useState();
    const [answers, setAnswers] = useState([]);

    let role = "ROLE_SUPPLIER";
    if (Cookies.get("user")) {
        role = JSON.parse(Cookies.get("user")).role;
    }

    useEffect( () => {
        if (props.image) {
            setImage(props.image);
        }
    }, [] );


    const addSubscription = async () => {
        try {
            const response = await axiosPrivate.post(`api/v1/order/${props.id}`);
            setOrderId(response.data.payload.orderId);
        } catch(err) {
            console.log(err);
        }
    }

    const onAddClick = (e) => {
        e.preventDefault();
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getQuestions = async () => {
            const URL = `/api/v1/form/question/${props.id}`;
            try {
                 const response = await axiosPrivate.get(URL, {
                     signal: controller.signal      //to allow to cansel a request
                 });
                 isMounted && setQuestions(response.data.payload);
                 isMounted && setQuestion(questions[i]);

            } catch(err) {
                 console.log(err);
            }
        }
        getQuestions();
        addSubscription();
        if (questions.length != 0) {
            setQuestionModalVisible(true);
        }

        return () =>{
         isMounted=false;
         controller.abort();
        }
    }

    const sendAnswer = async (answer, id) => {
        try {
            const response = await axiosPrivate.post(`/api/v1/form/answer`,
            {
                  formQuestionId: id,
                  orderId: orderId,
                  answer: answer
            });
        } catch(err) {
            console.log(err);
        }
    }


    const onAnswersSubmit = (e) => {
        e.preventDefault();
        console.log(i);
        console.log(questions.length);
        sendAnswer(answer, question.id);
        if (i<questions.length) {
            setI(i+1);
            setQuestion(questions[i]);
        }
        else{
            setQuestionModalVisible(false);
        }
        setAnswer("");
    }


    return (
        <>
            <div className={cl.card}>
                <div>
                    <img src={image} alt="Profile Image" className={cl.card_img} />
                    <p className={cl.card_price}>{props.price} {props.currency}</p>
                </div>
                <div className={cl.card_info}>
                    <h3 className={cl.card_heading}>{props.title} </h3>
                    <p className={cl.card_producer}> {props.supplier}</p>
                    <p className={cl.card_description}> {props.description}</p>
                    {role==="ROLE_CONSUMER"
                        ?
                        <button  className={cl.card_btn} onClick= {onAddClick}> add</button>
                        :
                        <> </>
                    }
                </div>
            </div>
            <Modal visible ={questionModalVisible} setVisible ={setQuestionModalVisible}>
                <form>
                        <Input id={question?.id} label={question?.question} onChange={(e) => setAnswer(e.target.value)} value = {answer}/>
                        <Button onClick = {onAnswersSubmit}> Submit </Button>
                </form>
            </Modal>
        </>
    );
};

export default ProductCard;