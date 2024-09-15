import React from 'react';
import { Container, Row, } from 'reactstrap';
import { memberData, teamData } from "./teamInfo";
import AboutCard from "./AboutCard";

export default function About() {
    return (
        <Container>
            <TeamCard />
            <MemberCards />
        </Container>
    );
}

function TeamCard() {
    return (
        <Row>
            <AboutCard
                title={teamData.teamName}
                text={teamData.missionStatement}
                pic={teamData.imagePath}
                team
            />
        </Row>
    );
}

function MemberCards() {
    return (
        <Row>
            {memberData.map(member =>
                <AboutCard
                    key={member.name}
                    title={member.name}
                    text={member.bio}
                    subTitle={member.homeTown}
                    pic={member.imagePath}
                />
            )}
        </Row>
    );
}