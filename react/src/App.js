import React, { Suspense } from 'react';
import { connect } from 'react-redux';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect,
} from 'react-router-dom';
import { IntlProvider } from 'react-intl';
import AppLocale from './lang';
import ColorSwitcher from './components/common/ColorSwitcher';
import { NotificationContainer } from './components/common/react-notifications';
import { isMultiColorActive, adminUI } from './constants/defaultValues';
import { getDirection } from './helpers/Utils';

const ViewApp = React.lazy(() =>
  import(/* webpackChunkName: "views-app" */ './views/app')
);

const ViewTime = React.lazy(() =>
  import(/* webpackChunkName: "views-app" */ './views/time')
);

const ViewUser = React.lazy(() =>
  import(/* webpackChunkName: "views-app" */ './views/user')
);

const ViewError = React.lazy(() =>
  import(/* webpackChunkName: "views-error" */ './views/error')
);

class App extends React.Component {
  constructor(props) {
    super(props);
    const direction = getDirection();
    if (direction.isRtl) {
      document.body.classList.add('rtl');
      document.body.classList.remove('ltr');
    } else {
      document.body.classList.add('ltr');
      document.body.classList.remove('rtl');
    }
  }

  render() {
    const { locale } = this.props;
    const currentAppLocale = AppLocale[locale];

    return (
      <div className="h-100">
        <IntlProvider
          locale={currentAppLocale.locale}
          messages={currentAppLocale.messages}
        >
          <>
            <NotificationContainer />
            {isMultiColorActive && <ColorSwitcher />}
            <Suspense fallback={<div className="loading" />}>
              <Router>
                <Switch>
                  <Route
                    path={adminUI}
                    render={(props) => <ViewApp {...props} />}
                  />
                  <Route
                    path="/ui/time"
                    render={(props) => <ViewTime {...props} />}
                  />
                  <Route
                    path="/ui/user"
                    render={(props) => <ViewUser {...props} />}
                  />
                  <Route
                    path="/error"
                    exact
                    render={(props) => <ViewError {...props} />}
                  />

                  <Redirect exact from="/" to="/app" />

                  <Redirect to="/error" />
                </Switch>
              </Router>
            </Suspense>
          </>
        </IntlProvider>
      </div>
    );
  }
}

const mapStateToProps = ({ settings }) => {
  const { locale } = settings;
  return { locale };
};
const mapActionsToProps = {};

export default connect(mapStateToProps, mapActionsToProps)(App);
