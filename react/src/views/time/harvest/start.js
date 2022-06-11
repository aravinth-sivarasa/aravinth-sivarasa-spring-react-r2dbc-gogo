import React from 'react';

import IconCard from 'components/cards/IconCard';
import { Colxx, Separator } from 'components/common/CustomBootstrap';
import Breadcrumb from 'containers/navs/Breadcrumb';
import { Card, CardBody, CardTitle, Row } from 'reactstrap'; //
import IntlMessages from 'helpers/IntlMessages';

const data = [
  {
    title: 'harvest.total',
    value: 74,
  },
];

const Start = ({ match }) => (
  <>
    <Row>
      <Colxx xxs="12">
        <Breadcrumb heading="menu.harvest" match={match} />
        <Separator className="mb-5" />
      </Colxx>
    </Row>
    <Row>
      <Colxx xl="6" lg="12" className="mb-4">
        <Row className="icon-cards-row mb-2">
          {data.map((item) => {
            return (
              <Colxx xl="3" lg="2" key={`icon_card_${item.title}`}>
                <IconCard {...item} className="mb-4" />
              </Colxx>
            );
          })}
        </Row>
      </Colxx>
      <Colxx xl="6" lg="12" className="mb-4">
        <Card className="h-100">
          <CardBody>
            <CardTitle>
              <IntlMessages id="dashboards.best-sellers" />
            </CardTitle>
            {/* <DataTable columns={cols} data={products} /> */}
          </CardBody>
        </Card>
      </Colxx>
    </Row>
  </>
);

export default Start;
