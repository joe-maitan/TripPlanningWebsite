import { useState } from 'react';

export function useToggle(starting) {
    const [toggled, setToggled] = useState(starting);

    function toggle() {
        setToggled(!toggled);
    }

    return [toggled, toggle];
}