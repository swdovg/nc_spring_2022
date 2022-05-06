import React from 'react';
import {getPagesArray} from "../../../utils/getPageCount";

const Pagination = ({totalPages, page, changePage}) => {
    let pagesArray = getPagesArray(totalPages);
    return (
        <div className="page__wrapper">
            {pagesArray.map(p =>
                <a
                    onClick={() => changePage(p)}
                    key={p}
                    className={page === p ? 'page page__current' : 'page'}
                >
                        {p}
                    </a>
            )}
        </div>
    );
};

export default Pagination;