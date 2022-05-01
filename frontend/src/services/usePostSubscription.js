import React, {useState, useEffect} from 'react';
import useAxiosPrivate from "../hook/useAxiosPrivate.js";
import Cookies from 'js-cookie';

function usePostSubscription(title, description, price, averageRating, category, categoryId)  {
    const URL = "api/v1/subscription";
    const supplier = JSON.parse(Cookies.get("user"));
    const axiosPrivate = useAxiosPrivate();

    let isMounted = true;
    const controller = new AbortController(); //to cansel request if the component on mounting

    const postSubscription = async () => {
        try {
             const response = await axiosPrivate.post(
                URL,
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
        } catch(err) {
             console.log(err);
        }
    }
    postSubscription();
    return () =>{
     isMounted=false;
     controller.abort();
    }
}
export default usePostSubscription;
