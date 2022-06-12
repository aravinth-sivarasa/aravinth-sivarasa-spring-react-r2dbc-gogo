/* eslint-disable no-nested-ternary */
/* eslint-disable react/jsx-key */
/* eslint-disable react/no-array-index-key */
/* eslint-disable react/destructuring-assignment */
/* eslint-disable react/display-name */
import React from 'react';

import products from 'data/products';

import WorkLogTblHeader from './worklog-tbl-header';

import DataTable from '../../util/DataTable';

export const ReactTableDivided = () => {
  const cols = React.useMemo(WorkLogTblHeader, []);
  return (
    <div className="mb-4">
      <DataTable columns={cols} data={products} divided />
    </div>
  );
};

export default ReactTableDivided;
