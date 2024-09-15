import React from 'react';
import { Button, Col, Container, Input, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'reactstrap';
import { useServerInputValidation } from '@hooks/useServerInputValidation';

export default function Settings(props) {
    const [serverInput, setServerInput, config, validServer, resetModal]
        = useServerInputValidation(props.serverSettings.serverUrl, props.toggleSettings);

    function closeModalWithoutSaving() {
        resetModal(props.serverSettings.serverUrl);
    }

    return (
        <Modal isOpen={props.showSettings} toggle={closeModalWithoutSaving}>
            <Header toggleOpen={closeModalWithoutSaving} />
            <Body
                serverInput={serverInput}
                setServerInput={setServerInput}
                serverSettings={props.serverSettings}
                serverName={getCurrentServerName(config, props.serverSettings)}
                validServer={validServer}
            />
            <Footer
                config={config}
                serverInput={serverInput}
                validServer={validServer}
                resetModal={resetModal}
                closeModalWithoutSaving={closeModalWithoutSaving}
                processServerConfigSuccess={props.processServerConfigSuccess}
            />
        </Modal>
    );
}

function getCurrentServerName(config, serverSettings) {
    if (config) {
        return config.serverName;
    }
    else if (serverSettings.serverConfig) {
        return serverSettings.serverConfig.serverName;
    }
    return "";
}

function Header(props) {
    return (
        <ModalHeader className="ml-2" toggle={props.toggleOpen}>
            Settings
        </ModalHeader>
    );
}

function Body(props) {
    const urlInput =
        <Input
            data-testid='server-settings-input"'
            value={props.serverInput}
            placeholder={props.serverSettings.serverUrl}
            onChange={(e) => { props.setServerInput(e.target.value) }}
            valid={props.validServer}
            invalid={!props.validServer}
        />;

    return (
        <ModalBody>
            <Container>
                <SettingsRow label="Name" value={props.serverName} />
                <SettingsRow label="URL" value={urlInput} />
                <SettingsRow 
                    label="Features" 
                    value={buildFeaturesList(props.serverSettings.serverConfig.features)} 
                />
            </Container>
        </ModalBody>
    );
}

function SettingsRow({label, value}) {
    return (
			<Row className='my-2 vertical-center'>
				<Col xs={3}>
                    {label}:
                </Col>
				<Col xs={9}>
					{label === 'Features' ? (
						value?.map((feature) => (
							<div key={`client-feature-${feature}`}>
								{feature}
								<br />
							</div>
						))
					) : (
						<div>{value}</div>
					)}
				</Col>
			</Row>
		);
}

function Footer(props) {
    return (
        <ModalFooter>
            <Button color="secondary" onClick={props.closeModalWithoutSaving}>Cancel</Button>
            <Button color="primary" data-testid='save-server-settings' onClick={() => {
                props.processServerConfigSuccess(props.config, props.serverInput);
                props.resetModal(props.serverInput);
            }}
                disabled={!props.validServer}
            >
                Save
            </Button>
        </ModalFooter>
    );
}

function buildFeaturesList(featuresList){
    return featuresList?.map(feature => feature.charAt(0).toUpperCase() + feature.slice(1)).sort();
}