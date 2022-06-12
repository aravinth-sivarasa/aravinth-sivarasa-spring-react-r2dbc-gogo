/* eslint-disable no-nested-ternary */
/* eslint-disable react/jsx-key */
/* eslint-disable react/no-array-index-key */
/* eslint-disable react/destructuring-assignment */
/* eslint-disable react/display-name */
/* eslint-disable react/react-in-jsx-scope */
const WorkLogTblHeader = () => [
  {
    Header: 'Date',
    accessor: 'sales',
    cellClass: 'list-item-heading w-20',
    Cell: (props) => <>{props.value}</>,
  },
  {
    Header: 'Location',
    accessor: 'name',
    cellClass: 'text-muted  w-40',
    Cell: (props) => <>{props.value}</>,
  },
  {
    Header: 'Hours',
    accessor: 'stock',
    cellClass: 'text-muted  w-10',
    Cell: (props) => <>{props.value}</>,
  },
];
export default WorkLogTblHeader;
