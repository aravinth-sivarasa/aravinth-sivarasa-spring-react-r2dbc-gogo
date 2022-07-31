/* eslint-disable no-nested-ternary */
/* eslint-disable react/jsx-key */
/* eslint-disable react/no-array-index-key */
/* eslint-disable react/destructuring-assignment */
/* eslint-disable react/display-name */
import React, { useState, useEffect } from 'react';

import WorkLogTblHeader from './worklog-tbl-header';

import DataTable from '../../util/DataTable';

export const ReactTableDivided = () => {
  const cols = React.useMemo(WorkLogTblHeader, []);
  const [products, setProducts] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch('http://universities.hipolabs.com/search?country=United+States')
      .then((res) => res.json())
      .then(
        (result) => {
          setIsLoaded(true);
          setProducts(result);
        },
        (apiError) => {
          setIsLoaded(true);
          setError(apiError);
        }
      );
  }, []);
  if (error) {
    return <div>Error: {error.message}</div>;
  }
  if (!isLoaded) {
    return <div>Loading...</div>;
  }
  return (
    <div className="mb-4">
      <DataTable columns={cols} data={products} divided />
    </div>
  );
};

export default ReactTableDivided;
