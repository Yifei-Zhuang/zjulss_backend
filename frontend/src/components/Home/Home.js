import React from 'react';
import BannerCarousel from './BannerCarousel.js';
import HeaderNavigation from './HeaderMenu.js';
import HomeProductSection from './HomeProductSection.js';

const Home = () => {
    return (
        <section>
            <HeaderNavigation/>
            <BannerCarousel />
            <HomeProductSection/>
        </section>
    )
}
export default Home
