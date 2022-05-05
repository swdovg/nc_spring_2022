import React, {useState, useEffect} from 'react';
import cl from './Pagination.module.css';



const Pagination = (props) => { return (
        <div className={cl.pagination}>
          <a href="#">&laquo;</a>
          <a href="#">1</a>
          <a href="#" class="active">2</a>
          <a href="#">3</a>
          <a href="#">4</a>
          <a href="#">5</a>
          <a href="#">6</a>
          <a href="#">&raquo;</a>
        </div>
        );
    };

export default Pagination;