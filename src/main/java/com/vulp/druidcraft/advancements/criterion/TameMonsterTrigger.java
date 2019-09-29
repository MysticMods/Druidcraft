package com.vulp.druidcraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vulp.druidcraft.entities.TameableMonster;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.TameAnimalTrigger;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

import javax.naming.Reference;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TameMonsterTrigger implements ICriterionTrigger<TameMonsterTrigger>, ICriterionInstance {
    private static final ResourceLocation ID = new ResourceLocation("tame_monster");
    private final Map<PlayerAdvancements, TameMonsterTrigger.Listeners> listeners = Maps.newHashMap();
    private TameMonsterTrigger entity;

    public TameMonsterTrigger(EntityPredicate any) {
    }

    public TameMonsterTrigger() {
    }

    public ResourceLocation getId() {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<TameMonsterTrigger> listener) {
        TameMonsterTrigger.Listeners tamemonstertrigger$listeners = this.listeners.get(playerAdvancementsIn);
        if (tamemonstertrigger$listeners == null) {
            tamemonstertrigger$listeners = new TameMonsterTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, tamemonstertrigger$listeners);
        }

        tamemonstertrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<TameMonsterTrigger> listener) {
        TameMonsterTrigger.Listeners tamemonstertrigger$listeners = this.listeners.get(playerAdvancementsIn);
        if (tamemonstertrigger$listeners != null) {
            tamemonstertrigger$listeners.remove(listener);
            if (tamemonstertrigger$listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }

    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    public TameMonsterTrigger deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        EntityPredicate entitypredicate = EntityPredicate.deserialize(json.get("entity"));
        return new TameMonsterTrigger(entitypredicate);
    }

    public void trigger(ServerPlayerEntity player, MonsterEntity entity) {
        TameMonsterTrigger.Listeners tamemonstertrigger$listeners = this.listeners.get(player.getAdvancements());
        if (tamemonstertrigger$listeners != null) {
            tamemonstertrigger$listeners.trigger(player, entity);
        }

    }

    public static class Instance extends CriterionInstance {
        private final EntityPredicate entity;

        public Instance(EntityPredicate entity) {
            super(TameMonsterTrigger.ID);
            this.entity = entity;
        }

        public static TameMonsterTrigger any() {
            return new TameMonsterTrigger(EntityPredicate.ANY);
        }

        public static TameMonsterTrigger func_215124_a(EntityPredicate p_215124_0_) {
            return new TameMonsterTrigger(p_215124_0_);
        }

        private boolean test(ServerPlayerEntity player, MonsterEntity entity) {
            return this.entity.test(player, entity);
        }

        public JsonElement serialize() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("entity", this.entity.serialize());
            return jsonobject;
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<TameMonsterTrigger>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<TameMonsterTrigger> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<TameMonsterTrigger> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(ServerPlayerEntity player, MonsterEntity entity) {
            List<ICriterionTrigger.Listener<TameMonsterTrigger>> list = null;

            for(ICriterionTrigger.Listener<TameMonsterTrigger> listener : this.listeners) {
                if (listener.getCriterionInstance().test(player, entity)) {
                    if (list == null) {
                        list = Lists.newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for(ICriterionTrigger.Listener<TameMonsterTrigger> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }

        }
    }

    private boolean test(ServerPlayerEntity player, MonsterEntity entity) {
        return this.entity.test(player, entity);
    }
}