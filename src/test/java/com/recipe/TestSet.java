package com.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.member.domain.Member;
import com.recipe.recipe.domain.Ingredient;
import com.recipe.recipe.domain.Recipe;
import com.recipe.like.repository.LikeRepository;
import com.recipe.member.repository.MemberRepository;
import com.recipe.recipe.dto.RecipeSaveForm;
import com.recipe.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

@AutoConfigureMockMvc
public class TestSet {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected RecipeRepository recipeRepository;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected LikeRepository likeRepository;

    protected final String USER_EMAIL = "test@email.com";
    protected final String USER_PASSWORD = "12345678";
    protected final String API_URL = "/api";

    protected RecipeSaveForm getRecipeSaveForm() {
/*        RecipeSaveForm recipeSaveForm = RecipeSaveForm.builder()
                .thumbnail("recipe-thumbnail")
                .title("recipe-title")
                .description("recipe-description")
                .fullDescription("recipe-full-description")
                .ingredients("tag1,tag2,tag3,tag4,tag5")
                .cookingTime(22)
                .member(memberRepository.findAll().get(0))
                .build();*/
        return new RecipeSaveForm(addRecipe());
    }

    protected Recipe addRecipe() {
        Recipe recipe = Recipe.builder()
                .thumbnail("test")
                .title("test-recipe")
                .fullDescription("test")
                .description("test")
                .ingredients(new HashSet<Ingredient>(Arrays.asList(new Ingredient("a"), new Ingredient("b"), new Ingredient("c"))))
                .cookingTime(11)
                .member(memberRepository.findByEmail(USER_EMAIL).orElseThrow())
                .build();

        Recipe save = recipeRepository.save(recipe);

        return save;
    }

    protected Member getMember() {
        return memberRepository.findByEmail(USER_EMAIL)
                .orElseThrow(() -> new UsernameNotFoundException(USER_EMAIL + "은 존재하지 않는 회원입니다."));
    }

}
