import React from 'react';
import cl from './Search.module.css';
import Button from "../button/Button";

const Search = (props) => {
    return (
        <form className={cl.search_form} action="" method="get">
            <input className={cl.search_input} name="search" placeholder="Search for subscription" type="search"
                onChange={(e)=> props.onUpdateSearchInput(e.target.value)}/>
            {/* <button className={cl.search_btn} type="submit">Search</button> */}
        </form>
    );
};

export default Search;