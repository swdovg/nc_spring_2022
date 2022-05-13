import React, {useState, useEffect} from 'react';
import cl from './Menu.module.css';
import Button from "../button/Button";
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";
import Cookies from 'js-cookie';

const Menu = (props) => {

    const axiosPrivate = useAxiosPrivate();
    const [categoryList, setCategoryList] = useState([]);

    const selectCategory = (e) =>{
        props.updateCategory(e.target.id);
    }

    useEffect( () => {
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getCategories = async () => {
            try {
                let response = await axiosPrivate.get("api/v1/category/0");
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

    return (
        <div className={cl.menu}>
            <ul className={cl.menu_box} {...props}>
                 <li className={cl.menu_item} onClick={(e) => props.updateCategory(null)}>All categories</li>
                 {categoryList?.map((item) =>
                    <li className={cl.menu_item} key={item.id} id={item.id} onClick={selectCategory}>
                        {item.name}
                    </li>)}
            </ul>
          </div>
    );
};

export default Menu;